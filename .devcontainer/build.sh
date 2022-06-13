time=`date '+%Y%m%d%H%M'`
repo='yuanzhibang/demo-env'

dockerFile='Dockerfile'

docker build -t $repo:default -f $dockerFile . 
docker image tag $repo:default $repo:$time
docker image tag $repo:default $repo:latest

docker push $repo:default
docker push $repo:latest
docker push $repo:$time
