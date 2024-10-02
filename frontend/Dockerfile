FROM node:latest as builder
WORKDIR /webapp
COPY ./package.json .
RUN npm install
COPY ./public ./public
COPY ./src ./src
COPY ./tsconfig.json .
ARG REACT_APP_SERVICE_URL
RUN npm run build

FROM nginx:mainline-alpine-slim
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /webapp/build /usr/share/nginx/html
