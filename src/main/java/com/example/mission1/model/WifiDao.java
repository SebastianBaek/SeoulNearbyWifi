package com.example.mission1.model;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WifiDao {

    private static final String url = "jdbc:mariadb://localhost:3306/mission1";
    private static final String id = "root";
    private static final String pass = "zerobase";

    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private static HikariDataSource dataSource;

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(id);
        config.setPassword(pass);

        config.setMinimumIdle(5);

        dataSource = new HikariDataSource(config);
    }

    public void getCon() {
        try {
            con = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertWifi(WifiBean wifiBean) {
        getCon();
        try {
            String sql = "insert into wifi values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
                    + ", ?, ?, ?, ?, ?) ";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, wifiBean.getNumber());
            pstmt.setString(2, wifiBean.getGu());
            pstmt.setString(3, wifiBean.getName());
            pstmt.setString(4, wifiBean.getDoroAddress());
            pstmt.setString(5, wifiBean.getDetailAddress());
            pstmt.setString(6, wifiBean.getFloor());
            pstmt.setString(7, wifiBean.getType());
            pstmt.setString(8, wifiBean.getAgency());
            pstmt.setString(9, wifiBean.getService());
            pstmt.setString(10, wifiBean.getNet());
            pstmt.setString(11, wifiBean.getInstallYear());
            pstmt.setString(12, wifiBean.getInOut());
            pstmt.setString(13, wifiBean.getAccess());
            pstmt.setDouble(14, wifiBean.getX());
            pstmt.setDouble(15, wifiBean.getY());
            pstmt.setString(16, wifiBean.getRecentWork());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if(con != null) {
                try {
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public int getWifiCount() {
        getCon();
        int result = 0;
        try {
            String sql = "select count(*) from wifi";

            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if(con != null) {
                try {
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return result;
    }

    public void insertMyLocation(double x, double y) {
        getCon();
        try {
            String sql = "insert into history values(null, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setDouble(1, x);
            pstmt.setDouble(2, y);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public List<HistoryBean> getAllHistory() {
        getCon();
        List<HistoryBean> historyBeanList = new ArrayList<>();
        try {
            String sql = "select * from history";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                HistoryBean historyBean = new HistoryBean();
                historyBean.setId(rs.getInt(1));
                historyBean.setX(rs.getDouble(2));
                historyBean.setY(rs.getDouble(3));
                historyBean.setUpdate(rs.getString(4));
                historyBeanList.add(historyBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return historyBeanList;
    }

    public void deleteHistory(int id) {
        getCon();
        try {
            String sql = "delete from history where id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if(con != null) {
                try {
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public List<ArrayList> getNearbyWifi(double x, double y) {
        getCon();
        List<ArrayList> nearbyWifiList = new ArrayList<>();
        try {
            String sql = " select *,\n" +
                    "    6371 * 2 * ASIN(SQRT(\n" +
                    "        POWER(SIN(RADIANS((? - y) / 2)), 2) +\n" +
                    "        COS(RADIANS(y)) * COS(RADIANS(?)) *\n" +
                    "        POWER(SIN(RADIANS((? - x) / 2)), 2)\n" +
                    "    )) AS distance_in_km\n" +
                    "from wifi order by distance_in_km limit 20 ";
            pstmt = con.prepareStatement(sql);
            pstmt.setDouble(1, x);
            pstmt.setDouble(2, x);
            pstmt.setDouble(3, y);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                WifiBean wifiBean = new WifiBean();
                ArrayList wifiAndDistance = new ArrayList();

                wifiBean.setNumber(rs.getString(1));
                wifiBean.setGu(rs.getString(2));
                wifiBean.setName(rs.getString(3));
                wifiBean.setDoroAddress(rs.getString(4));
                wifiBean.setDetailAddress(rs.getString(5));
                wifiBean.setFloor(rs.getString(6));
                wifiBean.setType(rs.getString(7));
                wifiBean.setAgency(rs.getString(8));
                wifiBean.setService(rs.getString(9));
                wifiBean.setNet(rs.getString(10));
                wifiBean.setInstallYear(rs.getString(11));
                wifiBean.setInOut(rs.getString(12));
                wifiBean.setAccess(rs.getString(13));
                wifiBean.setX(rs.getDouble(14));
                wifiBean.setY(rs.getDouble(15));
                wifiBean.setRecentWork(rs.getString(16));

                wifiAndDistance.add(wifiBean);
                wifiAndDistance.add(rs.getDouble(17));
                nearbyWifiList.add(wifiAndDistance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return nearbyWifiList;
    }
}
