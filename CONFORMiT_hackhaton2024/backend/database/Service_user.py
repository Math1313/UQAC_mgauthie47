from database.Setup import *


def register_user(username: str, password: str) -> User:
    try:
        print(f"Attempting to create user: {username}")
        user = User.create(Username=username, Password=password)
        user.save()
        print(f"User {username} created successfully")
    except Exception as e:
        print(f"Une erreur est survenue lors de la création de l'utilisateur: {e}")
        raise Exception("Internal error")
    return user


def login_user(username: str, password: str) -> User:
    try:
        print(f"Attempting to log in user: {username}")
        user = User.get(User.Username == username, User.Password == password)
        print(f"User {username} found")
    except User.DoesNotExist:
        print(f"User {username} not found")
        raise Exception("User not found")
    return user
