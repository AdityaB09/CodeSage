# ---------- Build stage ----------
FROM node:20-alpine AS build

# Set working directory
WORKDIR /app

# Install dependencies
COPY package.json package-lock.json* ./
RUN npm install

# Copy the rest of the frontend source and build
COPY . .
RUN npm run build

# ---------- Runtime stage ----------
FROM nginx:alpine

# Use your custom nginx config
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy built static files from build stage
COPY --from=build /app/dist /usr/share/nginx/html

# Expose container port
EXPOSE 80

# Start nginx in foreground
CMD ["nginx", "-g", "daemon off;"]
