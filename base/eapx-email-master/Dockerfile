FROM python:3.7-alpine
COPY requirements.txt /
RUN pip install -r /requirements.txt
WORKDIR /py
COPY *.py ./
ENTRYPOINT ["python3", "listener.py"]