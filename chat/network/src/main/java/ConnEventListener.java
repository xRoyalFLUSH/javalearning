public interface ConnEventListener {
    void onReceiveMessage(String message);
    void onConnectionError (CustConnection custConnection);
}
