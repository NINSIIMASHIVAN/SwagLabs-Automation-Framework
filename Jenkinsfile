pipeline {
    agent any
    tools {
        maven 'maven-3.9.15'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/NINSIIMASHIVAN/SwagLabs-Automation-Framework.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Reports') {
            steps {
                publishHTML(target: [
                    reportDir: 'src/test/resources',
                    reportFiles: 'ExtentReports.HTML',
                    reportName: 'Extent Spark Report'
                ])
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'src/test/resources/ExtentReports.HTML', fingerprint: true
            junit 'target/surefire-reports/*.xml'
        }
        success {
            emailext(
                to: 'ninsiimashivan319@gmail.com',
                subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <html>
                <body>
                <p>Hello Team,</p>
                <p>The latest Jenkins build has completed.</p>
                <p><b>Project Name:</b> ${env.JOB_NAME}</p>
                <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
                <p><b>Build Status:</b> <span style="color: green;"><b>SUCCESS</b></span></p>
                <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                <p><b>Last Commit:</b></p>
                <p>${env.GIT_COMMIT}</p>
                <p><b>Branch:</b> ${env.GIT_BRANCH}</p>
                <p><b>Build log is attached.</b></p>
                <p><b>Extent Report:</b> <a href="${env.BUILD_URL}Extent_20Spark_20Report/">Click here</a></p>
                <p>Best regards,</p>
                <p><b>Automation Team</b></p>
                </body>
                </html>
                """,
                mimeType: 'text/html',
                attachLog: true
            )
        }
        failure {
            emailext(
                to: 'ninsiimashivan319@gmail.com',Shirleykyeyune@gmail.com
                subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <html> <body>
                <p>Hello Team,</p>
                <p>The latest Jenkins build has <b style="color: red;">FAILED</b>.</p>
                <p><b>Project Name:</b> ${env.JOB_NAME}</p>
                <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
                <p><b>Build Status:</b> <span style="color: red;"><b>FAILED</b></span></p>
                <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                <p><b>Last Commit:</b></p>
                <p>${env.GIT_COMMIT}</p>
                <p><b>Branch:</b> ${env.GIT_BRANCH}</p>
                <p><b>Build log is attached.</b></p>
                <p><b>Please check the logs and take necessary actions.</b></p>
                <p><b>Extent Report (if available):</b> <a href="${env.BUILD_URL}Extent_20Spark_20Report/">Click here</a></p>
                <p>Best regards,</p>
                <p><b>Automation Team</b></p>
                </body>
                </html>
                """,
                mimeType: 'text/html',
                attachLog: true
            )
        }
    }
}