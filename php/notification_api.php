<?php

use Ratchet\Server\IoServer;
use Ratchet\Http\HttpServer;
use Ratchet\WebSocket\WsServer;
use Ratchet\MessageComponentInterface;
use Ratchet\ConnectionInterface;
use WebSocket\Client as WebSocketClient;

require_once __DIR__ . '/vendor/autoload.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // Get the query parameters
    $title = isset($_GET['title']) ? $_GET['title'] : null;
    $description = isset($_GET['description']) ? $_GET['description'] : null;
    $image = isset($_GET['image']) ? $_GET['image'] : null;
    $to = isset($_GET['to']) ? $_GET['to'] : null;

    // Check if all required parameters are present
    if (!$title || !$description || !$image) {
        http_response_code(400);
        echo json_encode(['message' => 'Missing required parameters']);
        exit();
    }

    // Send the notification
    $websocket = new WebSocketClient('ws://192.168.1.101:8080');
    $message = [
        'title' => $title,
        'description' => $description,
        'image' => $image,
        'to' => $to
    ];
    $websocket->send(json_encode($message));
    $websocket->close();

    echo json_encode(['message' => 'Notification sent successfully']);
} else {
    http_response_code(405);
    echo json_encode(['message' => 'Method not allowed']);
    exit();
}