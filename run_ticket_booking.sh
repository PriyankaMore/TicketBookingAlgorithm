echo "running nohup java -jar TicketBookingAlgorithm-0.0.1-SNAPSHOT.jar > logs.out"
nohup java -jar TicketBookingAlgorithm-0.0.1-SNAPSHOT.jar > logs.out &
echo "sleeping for 30 sec. If you want to see logs, they are present in logs.out  in current directory"
sleep 30s
echo "curl http://localhost:8080/generate/5/5"
curl http://localhost:8080/generate/5/5
echo "curl http://localhost:8080/holdSeats/5s"
curl http://localhost:8080/holdSeats/5

echo "If you want to proceed with booking the held seats, the URL can be found in the above response."
echo "If you wish to view the current state of the venue , use curl http://localhost:8080/display"
