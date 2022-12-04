package com.vemser.monitorservicosback.service;

import com.vemser.monitorservicosback.MonitorServicosBackApplication;
import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoCreateDTO;
import com.vemser.monitorservicosback.dto.aplicacao.AplicacaoDTO;
import com.vemser.monitorservicosback.enums.TipoDeploy;
import com.vemser.monitorservicosback.service.shell.ExecutarSh;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class DeployService {

    private static final String ARQUIVO_COMPLETO_TEMPLATE = "exemploCompleto.yaml";
    private static final String ARQUIVO_DOCKERFILE = "Dockerfile";
    private static final String ARQUIVO_NGINX_CONF = "nginx.conf";
    private static final String ARQUIVO_DOCKERIGNORE = ".dockerignore";

    private final AplicacaoService aplicacaoService;
    private final ExecutarSh executarSh;

    public String executarDeploy(AplicacaoCreateDTO aplicacaoCreateDTO) {
        try {
            AplicacaoDTO aplicacaoDTO = aplicacaoService.create(aplicacaoCreateDTO);

            switch (aplicacaoCreateDTO.getTipoDeploy()) {
                case SPRING ->
                        copiarDockerfileSpringBoot(aplicacaoCreateDTO.getWorkspace(), aplicacaoCreateDTO.getJavaOpts(), aplicacaoDTO.getCaminhoApp());
                case REACT -> {
                    copiarDockerfileReact(aplicacaoCreateDTO.getWorkspace());
                    copiarNginxConf(aplicacaoCreateDTO.getWorkspace());
                }
            }
            createArquivoKubernetesCompleto(aplicacaoCreateDTO.getWorkspace(),
                    aplicacaoDTO.getImagemDocker(),
                    aplicacaoDTO.getPorta().toString(),
                    aplicacaoDTO.getCaminhoApp(),
                    aplicacaoCreateDTO.getTipoDeploy());
            executarSh.executarDeployKub(aplicacaoDTO.getImagemDocker(), aplicacaoCreateDTO.getWorkspace());

            StringBuilder builder = new StringBuilder();
            builder.append("\n\n");
            builder.append("================================\n");
            builder.append("Publicado em ");
            builder.append(aplicacaoDTO.getUrl() + "\n");
            builder.append("Logs da app: " + aplicacaoDTO.getLog() + "\n");
            builder.append("================================");
            builder.append("\n\n");
            return builder.toString();
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException ex) {
            ex.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            return "Erro ao fazer deploy: " + sw;
        }
    }

    public void copiarDockerfileSpringBoot(String workspace, String javaOpts, String appPath) throws IOException {
        String caminho = TipoDeploy.SPRING.toString().toLowerCase() + "/" + ARQUIVO_DOCKERFILE;
        System.out.println("criando arquivo dockerfile");
        String fileContent = readFileToString(caminho);
        String url = "-Dspring.datasource.url=jdbc:oracle:thin:@10.0.20.80:1521/xe -Doracle.jdbc.timezoneAsRegion=false";
        String port = "-Dserver.port=8080";
        String profile = "-Dspring.profiles.active=hml";
        String appName = "-Dspring.application.name=" + appPath;
        String forwardHeader = "-Dserver.use-forward-headers=true -Dserver.forward-headers-strategy=framework -Dspringdoc.swagger-ui.path=/";
        fileContent = fileContent.replace("{{javaOpts}}", javaOpts + " "
                + url + " "
                + port + " "
                + profile + " "
                + appName + " "
                + forwardHeader);

        escreverNoArquivo(workspace + "/Dockerfile", fileContent, false);
    }

    private static void copiarDockerfileReact(String workspace) throws IOException {
        String caminho = TipoDeploy.REACT.toString().toLowerCase() + "/" + ARQUIVO_DOCKERFILE;
        System.out.println("criando arquivo dockerfile");
        String fileContent = readFileToString(caminho);

        escreverNoArquivo(workspace + "/Dockerfile", fileContent, false);

        caminho = TipoDeploy.REACT.toString().toLowerCase() + "/" + ARQUIVO_DOCKERIGNORE;
        System.out.println("criando arquivo .dockerignore");
        fileContent = readFileToString(caminho);

        escreverNoArquivo(workspace + "/.dockerignore", fileContent, false);
    }

    private static void copiarNginxConf(String workspace) throws IOException {
        String caminho = TipoDeploy.REACT.toString().toLowerCase() + "/" + ARQUIVO_NGINX_CONF;
        System.out.println("criando arquivo nginx.conf");
        String fileContent = readFileToString(caminho);

        escreverNoArquivo(workspace + "/nginx.conf", fileContent, false);
    }

    private static void escreverNoArquivo(String path, String fileContent, boolean createParent) throws IOException {
        File destino = new File(path);
        if (destino.exists()) {
            destino.delete();
        }
        if (createParent && !destino.getParentFile().exists()) {
            destino.getParentFile().mkdirs();
        }
        destino.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(destino));
        writer.append(fileContent);
        writer.close();
    }

    private static void createArquivoKubernetesCompleto(String workspace, String image, String port, String appPath, TipoDeploy tipoDeploy) throws IOException {
        System.out.println("criando arquivo de deploy completo");
        String fileContent = readFileToString(ARQUIVO_COMPLETO_TEMPLATE);
        fileContent = fileContent.replace("{{annotations}}", getAnnotations(tipoDeploy));
        fileContent = fileContent.replace("{{path}}", appPath);
        fileContent = fileContent.replace("{{image}}", image);
        fileContent = fileContent.replace("{{port}}", port);
        fileContent = fileContent.replace("{{targetPort}}", tipoDeploy == TipoDeploy.REACT ? "80" : "8080");

        escreverNoArquivo(workspace + "/k8s/complete-deployment.yaml", fileContent, true);
    }

    private static String getAnnotations(TipoDeploy tipoDeploy) {
        StringBuilder stringBuilder = new StringBuilder();
        if (TipoDeploy.SPRING.equals(tipoDeploy)) {
            stringBuilder.append("    nginx.ingress.kubernetes.io/x-forwarded-prefix: \"/{{path}}\"\n");
            stringBuilder.append("    nginx.ingress.kubernetes.io/rewrite-target: /$2\n");
            stringBuilder.append("    nginx.ingress.kubernetes.io/enable-cors: \"true\"\n");
        } else {
            stringBuilder.append("    nginx.ingress.kubernetes.io/rewrite-target: /$2\n");
        }
        return stringBuilder.toString();
    }

    private static String readFileToString(String file) throws IOException {
        try (InputStream inputStream = MonitorServicosBackApplication.class.getClassLoader().getResourceAsStream(file)) {
            return new String(inputStream.readAllBytes());
        }
    }
}
