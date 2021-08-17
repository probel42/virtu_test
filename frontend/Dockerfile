FROM node:16
ENV SERVER_URI=http://localhost:8080/backend
ENV APP_HOME=/usr/app/frontend
WORKDIR $APP_HOME
COPY rollup.config.js $APP_HOME
COPY package.json $APP_HOME
COPY package-lock.json $APP_HOME
COPY tsconfig.json $APP_HOME
COPY public/index.html $APP_HOME/public/
COPY public/global.css $APP_HOME/public/
COPY public/favicon.png $APP_HOME/public/
COPY src/ $APP_HOME/src
RUN mkdir -p $APP_HOME/public/build
RUN npm install
RUN yarn run build
