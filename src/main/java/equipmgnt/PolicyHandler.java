package equipmgnt;

import equipmgnt.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    @Autowired
    EquipmentRepository equipmentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverApprovalObtained_Decrease(@Payload ApprovalObtained approvalObtained){

        if(approvalObtained.isMe()){
            System.out.println("##### listener Decrease : " + approvalObtained.toJson());

            Optional<Equipment> equipmentOptional = equipmentRepository.findById(approvalObtained.getEquipmentId());

            Equipment equipment = equipmentOptional.get();
            equipment.setStock(equipment.getStock()-approvalObtained.getQty());


            equipmentRepository.save(equipment);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCancelRequested_Increased(@Payload CancelRequested cancelRequested){

        if(cancelRequested.isMe()){
            System.out.println("##### listener Increased : " + cancelRequested.toJson());
            Optional<Equipment> equipmentOptional = equipmentRepository.findById(cancelRequested.getEquipmentId());

            Equipment equipment = equipmentOptional.get();
            equipment.setStock(equipment.getStock()+cancelRequested.getQty());

            equipmentRepository.save(equipment);
        }
    }

}
