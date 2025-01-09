pipeline {
    agent any
    environment {
        MAVEN_HOME = '/opt/homebrew/Cellar/maven/3.9.9/libexec' // Đường dẫn đến thư mục Maven
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/anodtest/autotest.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                // Chuyển đến thư mục chứa pom.xml và chạy lệnh Maven
                dir('autotest') {
                    sh 'mvn clean install' // Chạy lệnh Maven trong thư mục myapp
                }
            }
        }

        stage('Test') {
            steps {
                // Chạy test và tạo báo cáo Allure
                // Chuyển đến thư mục và chạy kiểm thử
                dir('autotest') {
                    sh 'mvn test'
                }
            }
        }

        stage('Allure Report') {
            steps {
                // Tạo báo cáo Allure
                allure([
                    includeProperties: false,
                    jdk: '',
                    reportDir: 'allure-results', // Thư mục chứa kết quả
                    results: [[path: 'autotest/allure-results']] // Đường dẫn đến thư mục chứa file kết quả
                ])
            }
        }

        stage('Publish Allure Report') {
            steps {
                // Xuất báo cáo Allure
                allure([
                    reportDir: 'allure-results', // Thư mục chứa kết quả
                    reportPath: 'autotest/allure-results' // Đường dẫn đến thư mục chứa file kết quả
                ])
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed'
        }
    }
}
