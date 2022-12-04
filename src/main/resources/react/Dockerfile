FROM node:alpine AS builder

WORKDIR /app

#COPY ./build .
COPY . .

#ENTRYPOINT ["npx", "serve"]
ENTRYPOINT ["npm", "start"]

