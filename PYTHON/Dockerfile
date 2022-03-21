FROM python:3.7.7-slim

RUN mkdir -p /app
WORKDIR /app

COPY ./src/requirements.txt /app/requirements.txt
RUN pip install --upgrade pip \
  && pip install --upgrade pipenv\
  && apt-get clean \
  && apt-get update \
  && apt install -y build-essential \
  && apt install -y libmariadb3 libmariadb-dev \
  && pip install --upgrade -r requirements.txt

USER 1001

COPY ./src/ /app/
ENV FLASK_APP=server.py


CMD flask run -h 0.0.0.0 -p 5000
