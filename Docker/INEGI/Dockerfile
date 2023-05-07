FROM python:3.11.3-bullseye

WORKDIR /INEGI/src

RUN pip install --upgrade pip
RUN pip install -r requirements.txt

# Ejecuta el commando para que se ejecute el contenedor para desarrollo
CMD ["tail", "-f", "/dev/null"]