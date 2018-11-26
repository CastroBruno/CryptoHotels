import os
import time
import RPi.GPIO as GPIO
from bluetooth import *

GPIO.setmode(GPIO.BCM)
GPIO.setup(17, GPIO.OUT)

#while True:
#	print()	
#	time.sleep(1)

server_sock=BluetoothSocket( RFCOMM )
server_sock.bind(("",PORT_ANY))
server_sock.listen(1)

port = server_sock.getsockname()[1]

uuid = "91b085a1-4179-40c5-8766-b894bca9ffa9"

advertise_service( server_sock, "Door",
                   service_id = uuid,
                   service_classes = [ uuid, SERIAL_PORT_CLASS ],
                   profiles = [ SERIAL_PORT_PROFILE ], 
#                   protocols = [ OBEX_UUID ] 
                    )
while True:          
	print "Waiting for connection on RFCOMM channel %d" % port

	client_sock, client_info = server_sock.accept()
	print "Accepted connection from ", client_info

	try:
	        data = client_sock.recv(1024)
        	if len(data) == 0: break
	        print "received [%s]" % data

		if data == 'open':
			GPIO.output(17,True)
		elif data == 'close':
			GPIO.output(17,False)
		else:
			data = 'ERROR!' 
	        client_sock.send(data)
		print "sending [%s]" % data

	except IOError:
		pass

	except KeyboardInterrupt:
		print "disconnected"
		client_sock.close()
		server_sock.close()
		print "all done"

break