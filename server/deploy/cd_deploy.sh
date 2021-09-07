#
# cd_deploy.sh
#
# Jenkins와 같은 CD툴에 의해 jar 파일이 배포된 서버에서 실행될 스크립트입니다.
# 반드시 실행 전 deploy.sh로 파일 이름을 변경하고, 기본 디렉토리(우분투의 경우, /home/ubuntu)로 이동시켜주시기 바랍니다.
#

JAR_FILE_NAME=booknet-0.0.1-SNAPSHOT.jar

echo "Checking currently running process id..."
RUNNING_PROCESS_ID=$(pgrep -fl booknet | awk '{print $1}')

if [ -z "$RUNNING_PROCESS_ID" ]; then
    echo "No booknet server is running."
else
    echo "Killing process whose id is $RUNNING_PROCESS_ID"
    kill -15 $RUNNING_PROCESS_ID
    sleep 5
fi

echo "Running jar file..."
nohup java -jar $JAR_FILE_NAME > ~/nohup.out 2>&1 &
