pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }
    
    environment {
        DOCKER_IMAGE = 'booking-system'
        DOCKER_TAG = "${BUILD_NUMBER}"
        DOCKER_REGISTRY = 'ccr'
        MYSQL_ROOT_PASSWORD = credentials('mysql-root-password')
        MYSQL_DATABASE = credentials('mysql-database')
        MYSQL_USER = credentials('mysql-user')
        MYSQL_PASSWORD = credentials('mysql-password')
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from repository...'
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo 'Building the application...'
                script {
                    if (isUnix()) {
                        sh './mvnw clean package -DskipTests'
                    } else {
                        bat './mvnw.cmd clean package -DskipTests'
                    }
                }
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running unit tests...'
                script {
                    if (isUnix()) {
                        sh './mvnw test'
                    } else {
                        bat './mvnw.cmd test'
                    }
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    echo 'Test results published'
                }
            }
        }
        
        stage('Code Quality Analysis') {
            steps {
                echo 'Analyzing code quality...'
                // Add SonarQube analysis if configured
                // sh 'mvn sonar:sonar'
            }
        }
        
        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                    docker.build("${DOCKER_IMAGE}:latest")
                }
            }
        }
        
        stage('Docker Push') {
            steps {
                echo 'Pushing Docker image to registry...'
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials') {
                        docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").push()
                        docker.image("${DOCKER_IMAGE}:latest").push()
                    }
                }
            }
        }
        
        stage('Deploy to Staging') {
            steps {
                echo 'Deploying to staging environment...'
                script {
                    if (isUnix()) {
                        sh 'docker-compose down'
                        sh 'docker-compose up -d'
                    } else {
                        bat 'docker-compose down'
                        bat 'docker-compose up -d'
                    }
                }
            }
        }
        
        stage('Health Check') {
            steps {
                echo 'Performing health check...'
                script {
                    sleep(time: 30, unit: 'SECONDS')
                    if (isUnix()) {
                        sh 'curl -f http://localhost:8080/actuator/health || exit 1'
                    } else {
                        bat 'powershell -Command "Invoke-WebRequest -Uri http://localhost:8080 -UseBasicParsing"'
                    }
                }
            }
        }
        
        stage('Deploy to Production') {
            when {
                branch 'main'
            }
            steps {
                input message: 'Deploy to Production?', ok: 'Deploy'
                echo 'Deploying to production environment...'
                script {
                    // Add production deployment steps
                    echo 'Production deployment would happen here'
                }
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline execution completed'
            cleanWs()
        }
        success {
            echo 'Pipeline executed successfully!'
            // Add notification (email, Slack, etc.)
        }
        failure {
            echo 'Pipeline failed!'
            script {
                // Rollback to previous version
                echo 'Rolling back to previous stable version...'
                if (isUnix()) {
                    sh 'docker-compose down'
                    sh 'docker pull ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:latest'
                    sh 'docker-compose up -d'
                } else {
                    bat 'docker-compose down'
                    bat 'docker pull ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:latest'
                    bat 'docker-compose up -d'
                }
            }
        }
        unstable {
            echo 'Pipeline is unstable'
        }
    }
}
