FROM python:3.9-slim

WORKDIR /app

COPY data_viz_service/requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY data_viz_service/ .

EXPOSE 5000

CMD ["python", "app.py"]
