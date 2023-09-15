FROM node:current-alpine3.18
WORKDIR /frontend

COPY package*.json ./
RUN npm install
COPY . ./
RUN npm run build

CMD [ "npm", "start" ]