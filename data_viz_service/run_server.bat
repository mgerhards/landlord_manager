@echo off
cd /d c:\Users\gerha\workspace\landlord_manager\data_viz_service
call venv\Scripts\activate
uvicorn main:app --reload