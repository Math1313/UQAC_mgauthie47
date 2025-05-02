import json

from chainlit.auth import create_jwt
from chainlit.user import User
from chainlit.utils import mount_chainlit
from dotenv import load_dotenv
from fastapi import FastAPI, Request, HTTPException
from fastapi.responses import JSONResponse
from pydantic import ValidationError
from starlette.middleware.cors import CORSMiddleware

from database.Service_user import login_user
from database.Service_user import register_user
from database.Setup import init_db
from request.LoginData import LoginData
from request.RegisterData import RegisterData

load_dotenv()

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*", "http://localhost:5173"],  # Allows all origins
    allow_credentials=True,
    allow_methods=["*"],  # Allows all methods
    allow_headers=["*"],  # Allows all headers
)

# Initialisation de la base de données
init_db()

# Création de l'utilisateur admin
register_user("admin", "admin")


@app.get("/custom-auth")
async def custom_auth():
    # Verify the user's identity with custom logic.
    token = create_jwt(User(identifier="Test User"))
    return JSONResponse({"token": token})


# Route pour l'authentification
@app.post("/login")
async def login(request: Request):
    try:
        login_data = await request.json()
        login_data = LoginData(**login_data)
    except json.JSONDecodeError:
        raise HTTPException(status_code=400, detail="Invalid JSON")
    except ValidationError as e:
        raise HTTPException(status_code=400, detail=str(e))
    except Exception:
        raise HTTPException(status_code=500, detail="Internal Server Error")

    try:
        user = login_user(login_data.username, login_data.password)
    except Exception:
        raise HTTPException(status_code=401, detail="Invalid credentials")

    return JSONResponse({"token": create_jwt(User(identifier=user.Username))})


@app.post("/register")
async def register(request: Request):
    try:
        register_data = await request.json()
        register_data = RegisterData(**register_data)
    except json.JSONDecodeError:
        raise HTTPException(status_code=400, detail="Invalid JSON")
    except ValidationError:
        raise HTTPException(status_code=400, detail="Invalid JSON")
    except Exception:
        raise HTTPException(status_code=500, detail="Internal Server Error")

    try:
        user = register_user(register_data.username, register_data.password)
    except Exception:
        raise HTTPException(status_code=500, detail="Internal Server Error")

    return JSONResponse({"token": create_jwt(User(identifier=user.Username))})


mount_chainlit(app=app, target="cl_app.py", path="/chainlit")
