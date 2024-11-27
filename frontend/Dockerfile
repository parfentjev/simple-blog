FROM node:latest as builder

WORKDIR /webapp
COPY ./package.json .
RUN npm install

ARG REACT_APP_SERVICE_URL
ARG REACT_APP_MEDIA_URL

COPY ./public ./public
COPY ./src ./src
COPY ./tsconfig.json .
RUN npm run build

FROM nginx:mainline-alpine-slim
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /webapp/build /usr/share/nginx/html
