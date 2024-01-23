package wb.plugin.wbutils.io;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface DatabaseConnectionManager {

    CompletableFuture<Connection> getConnectionAsync();

    CompletableFuture<Void> closeConnectionAsync(final Connection connection);

    default CompletableFuture<Void> closeAllConnectionsAsync() {
        return CompletableFuture.runAsync(() -> {
            if (this instanceof Closeable dataSource) {
                try {
                    dataSource.close();
                } catch (IOException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing DataSource", e);
                }
            }
        });
    }
}
