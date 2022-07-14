package dev.group3.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.Service;
import dev.group3.model.enums.ServiceType;
import dev.group3.util.ConnectionUtil;

public class ResourcesDAO {
    
    private ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
    private static Logger log = LogManager.getLogger(ResourcesDAO.class);
    
    /*
     * === READ ===
     */
    
    /**
     * Retrieves all the services from the services table in the database.
     * @return A list of services if successful, and null otherwise.
     */
    public List<Service> getAllServices() {
        log.debug("Attempting to get all services from database");
        
        // Init
        String sql = "select * from services";
        List<Service> services = null;
        
        // Attempting to execute query
        try (Connection conn = conUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Successfully retrieved services
                services = new ArrayList<Service>();
                do {
                    services.add(createServiceFromResultSet(rs));
                } while (rs.next());
            }
        } catch (SQLException e) {
            log.error("Failed to execute query: " + sql);
            e.printStackTrace();
        }
        
        return services;
    }
    
    /*
     * === UTILITY ===
     */
    
    /**
     * Creates a service object from the given result set
     * Assumes result set is valid and contains necessary data
     * @param rs The result set to create the Service from
     * @return A new service
     * @throws SQLException
     */
    private Service createServiceFromResultSet(ResultSet rs) throws SQLException {
        return new Service(
                rs.getInt("id"),
                ServiceType.valueOf(rs.getString("service_type")),
                rs.getInt("duration_hour"),
                rs.getDouble("price"));
    }
}
