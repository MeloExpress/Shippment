package com.example.MeloExpress.Shippment.Kafka;

import com.example.MeloExpress.Shippment.service.CollectService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    @Autowired
    private CollectService collectService;

    @KafkaListener(topics = "routing-event", groupId = "shippment-api")
    public void listen(GenericMessage<String> genericMessage) {
        String payload = genericMessage.getPayload();
        System.out.println("Received message: " + payload);

        // Remover o trecho indesejado da mensagem recebida
        String mensagemJson = payload.substring(payload.indexOf("{"));
        System.out.println("Mensagem JSON: " + mensagemJson);

        try {
            // Parse da mensagem recebida para um objeto JSON
            JSONObject mensagem = new JSONObject(mensagemJson);
            System.out.println("Mensagem JSON parseada: " + mensagem);

            // Obtenção da lista collectDetailsList
            JSONArray collectDetailsList = mensagem.getJSONArray("collectDetailsList");

            // Iteração sobre cada objeto na lista collectDetailsList
            for (int i = 0; i < collectDetailsList.length(); i++) {
                JSONObject collectDetails = collectDetailsList.getJSONObject(i);
                String collectCode = collectDetails.getString("routingCollectCode");
                System.out.println("routingCollectCode: " + collectCode);

                // Atualização do status do collect para "ROTEIRIZADA"
                collectService.updateCollectStatus(collectCode);
            }
        } catch (JSONException e) {
            System.out.println("Erro ao processar a mensagem JSON: " + e.getMessage());
        }
    }



}






