pipeline {
    agent any
    
    environment {
        // Docker image tags
        APP_NAME = 'swe7303-travel-booking'
        DOCKER_IMAGE = "${APP_NAME}:${BUILD_NUMBER}"
        
        // Database credentials (in production, use Jenkins credentials)
        MYSQL_ROOT_PASSWORD = 'Shisir.55'
        MYSQL_DATABASE = 'devops'
    }
    
    stages {
        // Stage 1: Checkout code
        stage('Checkout') {
            steps {
                git branch: 'main', 
                    url: 'https://github.com/YOUR_USERNAME/swe7303-travel-booking.git',
                    credentialsId: 'github-token'
            }
        }
        
        // Stage 2: Build with Maven
        stage('Maven Build') {
            steps {
                sh 'mvn clean compile -DskipTests'
            }
        }
        
        // Stage 3: Run Tests
        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        
        // Stage 4: Build Docker Image
        stage('Build Docker Image') {
            steps {
                script {
                    sh '''
                        echo "Building Docker image..."
                        docker build -t ${DOCKER_IMAGE} .
                        docker tag ${DOCKER_IMAGE} ${APP_NAME}:latest
                    '''
                }
            }
        }
        
        // Stage 5: Run with Docker Compose
        stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh '''
                        echo "Stopping existing containers..."
                        docker-compose down || true
                        
                        echo "Starting containers..."
                        docker-compose up -d
                        
                        echo "Waiting for services to start..."
                        sleep 30
                    '''
                }
            }
        }
        
        // Stage 6: Verify Deployment
        stage('Verify Deployment') {
            steps {
                script {
                    sh '''
                        echo "Checking if application is running..."
                        curl -f http://localhost:8080/ || exit 1
                        echo " Application is running!"
                        
                        echo "Checking containers..."
                        docker ps
                    '''
                }
            }
        }
    }
    
    post {
        success {
            echo ' Pipeline completed successfully!'
            emailext (
                subject: "SUCCESS: Pipeline ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Build ${env.BUILD_NUMBER} was successful!\n\nCheck: ${env.BUILD_URL}",
                to: 'your-email@example.com'
            )
        }
        failure {
            echo ' Pipeline failed!'
            emailext (
                subject: "FAILURE: Pipeline ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Build ${env.BUILD_NUMBER} failed!\n\nCheck: ${env.BUILD_URL}",
                to: 'your-email@example.com'
            )
        }
        always {
            echo ' Cleaning up workspace...'
            cleanWs()
        }
    }
}
