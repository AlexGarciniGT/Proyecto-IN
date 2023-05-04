FROM python:3.11.3-slim-bullseye

WORKDIR /requests
COPY requirements.txt requirements.txt
RUN pip3 install -r requirements.txt

COPY . .
CMD ["python3", "-m" , "src", "run", "--host=0.0.0.0"]