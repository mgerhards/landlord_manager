# Use an official Node.js image as a base
FROM node:18-alpine

# Set the working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy the rest of the frontend code
COPY . .

# Build the frontend
RUN npm run build

# Expose the frontend port
EXPOSE 5173

# Start the frontend server
CMD ["npm", "run", "preview"]