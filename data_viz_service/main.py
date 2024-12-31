from fastapi import FastAPI, Depends
from fastapi.security import OAuth2PasswordBearer, OAuth2PasswordRequestForm

app = FastAPI()

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

@app.get("/")
async def read_root():
    return {"message": "Welcome to the Microservice!"}

@app.get("/secure-endpoint")
async def secure_endpoint(token: str = Depends(oauth2_scheme)):
    return {"message": "This is a secure endpoint.", "token": token}