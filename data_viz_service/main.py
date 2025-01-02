import requests
from fastapi import FastAPI, Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="http://your-spring-boot-backend/oauth2/token")

SPRING_BOOT_TOKEN_VALIDATION_URL = "http://your-spring-boot-backend/oauth2/check_token"

def validate_token_with_spring(token: str):
    response = requests.get(SPRING_BOOT_TOKEN_VALIDATION_URL, params={"token": token})
    if response.status_code != 200:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Invalid token",
        )
    token_data = response.json()
    if not token_data.get("active"):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Token is not active",
        )
    return token_data

def get_current_user(token: str = Depends(oauth2_scheme)):
    token_data = validate_token_with_spring(token)
    return token_data.get("username")

app = FastAPI()

@app.get("/secure-endpoint")
async def secure_endpoint(username: str = Depends(get_current_user)):
    return {"message": f"Hello, {username}! This is a secure endpoint."}

@app.get("/public-endpoint")
async def public_endpoint():
    return {"message": "This is a public endpoint."}