# Docker Deployment Guide

This guide explains how to run the SWE7303 Travel Booking application using Docker containers.

## Prerequisites

- Docker Desktop installed and running
- Docker Compose installed (included with Docker Desktop)
- Ports 8080 and 3307 available on your machine

## Project Structure

```
swe7303/
├── Dockerfile              # Application container definition
├── docker-compose.yml      # Multi-container orchestration
├── .dockerignore          # Files to exclude from Docker build
└── README-Docker.md       # This file
```

## Quick Start

### Option 1: Using Docker Compose (Recommended)

This will start both MySQL database and the Spring Boot application:

```bash
# Build and start all containers
docker-compose up --build

# Or run in detached mode (background)
docker-compose up -d --build
```

The application will be available at: `http://localhost:8080`

### Option 2: Manual Docker Build

If you prefer to build and run containers individually:

```bash
# 1. Start MySQL container
docker run -d \
  --name swe7303-mysql \
  -e MYSQL_ROOT_PASSWORD=Shisir.55 \
  -e MYSQL_DATABASE=devops \
  -p 3307:3306 \
  mysql:8.0

# 2. Build application image
docker build -t swe7303-app .

# 3. Run application container
docker run -d \
  --name swe7303-app \
  --link swe7303-mysql:mysql \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/devops \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=Shisir.55 \
  -p 8080:8080 \
  swe7303-app
```

## Docker Commands

### View running containers
```bash
docker-compose ps
```

### View logs
```bash
# All services
docker-compose logs -f

# Application only
docker-compose logs -f app

# MySQL only
docker-compose logs -f mysql
```

### Stop containers
```bash
docker-compose down
```

### Stop and remove volumes (delete database data)
```bash
docker-compose down -v
```

### Rebuild containers
```bash
docker-compose up --build --force-recreate
```

### Access MySQL database
```bash
docker exec -it swe7303-mysql mysql -uroot -pShisir.55 devops
```

### Access application container shell
```bash
docker exec -it swe7303-app sh
```

## Container Details

### MySQL Container
- **Image**: mysql:8.0
- **Container Name**: swe7303-mysql
- **Host Port**: 3307
- **Container Port**: 3306
- **Database Name**: devops
- **Root Password**: Shisir.55
- **Data Volume**: mysql-data (persistent storage)

### Application Container
- **Base Image**: eclipse-temurin:21-jre-alpine
- **Container Name**: swe7303-app
- **Host Port**: 8080
- **Container Port**: 8080
- **Build Type**: Multi-stage (optimized size)

## Troubleshooting

### Port conflicts
If ports 8080 or 3307 are already in use:
- Edit `docker-compose.yml` and change the host port (left side of colon)
- Example: `"8081:8080"` or `"3308:3306"`

### Database connection issues
```bash
# Check if MySQL is ready
docker-compose logs mysql | grep "ready for connections"

# Restart the application
docker-compose restart app
```

### Application won't start
```bash
# View detailed logs
docker-compose logs app

# Rebuild from scratch
docker-compose down -v
docker-compose up --build
```

### Clear everything and start fresh
```bash
# Remove all containers and volumes
docker-compose down -v

# Remove images
docker rmi swe7303-app
docker rmi mysql:8.0

# Rebuild
docker-compose up --build
```

## Production Considerations

For production deployment, consider:

1. **Environment Variables**: Use `.env` file instead of hardcoded values
2. **Secrets Management**: Use Docker secrets or external vault
3. **Reverse Proxy**: Add Nginx container for SSL/TLS
4. **Health Checks**: Already configured for MySQL
5. **Resource Limits**: Add memory and CPU constraints
6. **Logging**: Configure external log aggregation
7. **Backup**: Set up automated MySQL backups

## Network Configuration

The application uses a custom bridge network (`swe7303-network`) for container communication. This provides:
- DNS resolution between containers
- Network isolation
- Better security

## Data Persistence

MySQL data is stored in a Docker volume (`mysql-data`) which persists even when containers are stopped or removed. To completely reset the database, use:

```bash
docker-compose down -v
```

## Testing the Application

Once containers are running:

1. Open browser: `http://localhost:8080`
2. Create an account (signup)
3. Login with Customer or Admin role
4. Test booking functionality

## Stopping the Application

```bash
# Stop containers (data preserved)
docker-compose stop

# Stop and remove containers (data preserved)
docker-compose down

# Stop, remove containers and delete all data
docker-compose down -v
```
