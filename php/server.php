<?php
use Ratchet\Server\IoServer;
use Ratchet\Http\HttpServer;
use Ratchet\WebSocket\WsServer;
use Ratchet\MessageComponentInterface;
use Ratchet\ConnectionInterface;

require_once __DIR__ . '/vendor/autoload.php';

class NotificationServer implements MessageComponentInterface
{

	protected $clients;
    protected $usernames;
    protected $lastUserId = 0;

    public function __construct()
    {
        $this->clients = new \SplObjectStorage;
        $this->usernames = [];
    }
	
	public function onOpen(ConnectionInterface $conn)
    {
        // Store the new connection to send messages to later
        $this->clients->attach($conn);

        // Assign a unique ID to the connection
        $conn->userId = ++$this->lastUserId;

        echo "New connection! (User ID: {$conn->userId})\n";
    }
	
	 public function onMessage(ConnectionInterface $from, $msg)
    {
        $numRecv = count($this->clients) - 1;

        // Decode the JSON message
        $data = json_decode($msg, true);

        // Send a notification to all clients
        $notification = [
            'title' => $data['title'],
            'description' => $data['description'],
            'image' => $data['image'],
        ];

        foreach ($this->clients as $client) {
            $client->send(json_encode($notification));
        }

    }

	public function onClose(ConnectionInterface $conn)
    {
        // Remove the connection from the list of connected clients
        $this->clients->detach($conn);

        echo "Connection {$conn->userId} has disconnected\n";
    }
	
    public function onError(ConnectionInterface $conn, \Exception $e)
    {
        echo "An error has occurred: {$e->getMessage()}\n";
        $conn->close();
    }
	
}

$server = IoServer::factory(
    new HttpServer(
        new WsServer(
            new NotificationServer()
        )
    ),
    8080
);

// Start the server
$server->run();