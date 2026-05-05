pipeline {
    agent any

    tools {
        maven 'Maven 3.9.0'
        jdk 'JDK 21'
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select browser to run tests')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Select environment')
        booleanParam(name: 'HEADLESS', defaultValue: false, description: 'Run tests in headless mode')
        string(name: 'THREAD_COUNT', defaultValue: '4', description: 'Number of parallel threads')
    }

    environment {
        MAVEN_OPTS = '-Xmx1024m'
        ALLURE_RESULTS = 'exports/AllureReport'
        EXTENT_REPORTS = 'exports/ExtentReports'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                echo 'Cleaning previous build artifacts...'
                bat 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling project...'
                bat 'mvn compile'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Running tests on ${params.ENVIRONMENT} environment with ${params.BROWSER} browser"
                script {
                    def headlessFlag = params.HEADLESS ? 'true' : 'false'
                    bat """
                        mvn test -P${params.ENVIRONMENT} ^
                        -Dbrowser=${params.BROWSER} ^
                        -Dheadless=${headlessFlag} ^
                        -Dthread.count=${params.THREAD_COUNT}
                    """
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                echo 'Generating Allure Report...'
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: "${ALLURE_RESULTS}"]]
                    ])
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                echo 'Publishing Extent Report...'
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: "${EXTENT_REPORTS}",
                    reportFiles: 'ExtentReport.html',
                    reportName: 'Extent Report',
                    reportTitles: 'Test Execution Report'
                ])
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo 'Archiving test artifacts...'
                archiveArtifacts artifacts: 'exports/**/*', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
        success {
            echo 'Build completed successfully!'
            emailext(
                subject: "Jenkins Build Success: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: """
                    <h2>Build Success</h2>
                    <p><strong>Job:</strong> ${env.JOB_NAME}</p>
                    <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                    <p><strong>Environment:</strong> ${params.ENVIRONMENT}</p>
                    <p><strong>Browser:</strong> ${params.BROWSER}</p>
                    <p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """,
                to: '${DEFAULT_RECIPIENTS}',
                mimeType: 'text/html'
            )
        }
        failure {
            echo 'Build failed!'
            emailext(
                subject: "Jenkins Build Failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: """
                    <h2>Build Failed</h2>
                    <p><strong>Job:</strong> ${env.JOB_NAME}</p>
                    <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                    <p><strong>Environment:</strong> ${params.ENVIRONMENT}</p>
                    <p><strong>Browser:</strong> ${params.BROWSER}</p>
                    <p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                    <p><strong>Console Output:</strong> <a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a></p>
                """,
                to: '${DEFAULT_RECIPIENTS}',
                mimeType: 'text/html'
            )
        }
    }
}
