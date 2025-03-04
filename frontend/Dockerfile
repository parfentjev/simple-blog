FROM node:latest AS builder

ARG VITE_SERVICE_URL
ARG VITE_MEDIA_URL
WORKDIR /webapp
COPY . .
RUN npm install
RUN npm run build

FROM nginx:mainline-alpine-slim
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /webapp/dist /usr/share/nginx/html
