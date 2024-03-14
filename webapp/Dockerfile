FROM node:latest as builder
WORKDIR /webapp
COPY ./package.json .
RUN npm install
COPY ./public ./public
COPY ./src ./src
COPY ./tsconfig.json .
RUN npm run build

FROM nginx:mainline-alpine-slim
COPY --from=builder /webapp/build /usr/share/nginx/html
