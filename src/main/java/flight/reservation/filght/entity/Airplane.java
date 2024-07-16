package flight.reservation.filght.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Data
@DynamicInsert
public class Airplane {

    // 항공기ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int airplaneId;

    // 연관관계 설정
    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL)
    private List<Flight> flights;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL)
    private List<FlightHistory> flightHistories;

    // 수용가능인원
    private int capacity;

    // 항공기종류
    @Column(length = 20)
    private String airplaneType;

    // 사용여부
    @Column(length = 1)
    @ColumnDefault("'Y'")
    private String useyn;
}
