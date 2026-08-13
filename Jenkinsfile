pipeline {
    agent any

    tools {
        maven 'maven-3.9.15'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/NINSIIMASHIVAN/SwagLabs-Automation-Framework.git'
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
                    reportFiles: 'ExtentReports.html',
                    reportName: 'Extent Spark Report',
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true
                ])
            }
        }
    }

    post {

        always {

            archiveArtifacts(
                artifacts: 'src/test/resources/ExtentReports.html',
                fingerprint: true,
                allowEmptyArchive: true
            )

            junit(
                testResults: 'target/surefire-reports/*.xml',
                allowEmptyResults: true
            )
        }

        success {

            emailext(
               to: 'ninsiimashivan319@gmail.com, Shirleykyeyune@gmail.com',

                subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",

                body: """
                <html>
                <body>

                <p>Hello Team,</p>

                <p>The latest Jenkins build has completed successfully.</p>

                <p>
                <b>Project Name:</b> ${env.JOB_NAME}
                </p>

                <p>
                <b>Build Number:</b> #${env.BUILD_NUMBER}
                </p>

                <p>
                <b>Build Status:</b>
                <span style="color: green;">
                <b>SUCCESS</b>
                </span>
                </p>

                <p>
                <b>Build URL:</b>
                <a href="${env.BUILD_URL}">
                ${env.BUILD_URL}
                </a>
                </p>

                <p>
                <b>Last Commit:</b>
                ${env.GIT_COMMIT}
                </p>

                <p>
                <b>Branch:</b>
                ${env.GIT_BRANCH}
                </p>

                <p>
                <b>Extent Report:</b>
                Check the published report from the Jenkins build page.
                </p>

                <p>
                Best regards,<br>
                <b>Automation Team</b>
                </p>

                </body>
                </html>
                """,

                mimeType: 'text/html',
                attachLog: true
            )
        }

        failure {

            emailext(
                to: 'ninsiimashivan319@gmail.com',

                subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",

                body: """
                <html>
                <body>

                <p>Hello Team,</p>

                <p>
                The latest Jenkins build has
                <span style="color: red;">
                <b>FAILED</b>
                </span>.
                </p>

                <p>
                <b>Project Name:</b> ${env.JOB_NAME}
                </p>

                <p>
                <b>Build Number:</b> #${env.BUILD_NUMBER}
                </p>

                <p>
                <b>Build URL:</b>
                <a href="${env.BUILD_URL}">
                ${env.BUILD_URL}
                </a>
                </p>

                <p>
                <b>Last Commit:</b>
                ${env.GIT_COMMIT}
                </p>

                <p>
                <b>Branch:</b>
                ${env.GIT_BRANCH}
                </p>

                <p>
                <b>Please check the Jenkins console log for details.</b>
                </p>

                <p>
                Best regards,<br>
                <b>Automation Team</b>
                </p>

                </body>
                </html>
                """,

                mimeType: 'text/html',
                attachLog: true
            )
        }
    }
}