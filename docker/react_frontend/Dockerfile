FROM node:16-alpine

WORKDIR /app

# Copy the rest of the application
COPY react_frontend/ .

# Install dependencies
RUN npm install

EXPOSE 5173

ARG APP_RUNTYPE
ENV RUNTYPE=${APP_RUNTYPE}

# Remove build step and directly run dev/prod
CMD ["sh", "-c", "if [ \"$APP_RUNTYPE\" = \"prod\" ]; then npm run preview; else npm run dev; fi"]

