package equipmgnt;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Equipment_table")
public class Equipment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Integer stock;
    private String status;

    @PostPersist
    public void onPostPersist(){
        EquipmentRegistered equipmentRegistered = new EquipmentRegistered();
        BeanUtils.copyProperties(this, equipmentRegistered);
        equipmentRegistered.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){
/*
        if(this.getStatus().equals("CANCELLED")){
            StockIncreased stockIncreased = new StockIncreased();
            BeanUtils.copyProperties(this, stockIncreased);
            stockIncreased.publishAfterCommit();
        } else{
            StockDecreased stockDecreased = new StockDecreased();
            BeanUtils.copyProperties(this, stockDecreased);
            stockDecreased.publishAfterCommit();
        }
*/
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
