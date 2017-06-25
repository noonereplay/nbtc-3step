package cat.nbtc.step.sms.listen;

import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.MessageType;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.Session;
import org.jsmpp.util.InvalidDeliveryReceiptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class SmscListener implements MessageReceiverListener{
	
	
	private static final Logger logger = LoggerFactory.getLogger(SmscListener.class);

	@Override
	public DataSmResult onAcceptDataSm(DataSm dataSm, Session session) throws ProcessRequestException {
		// TODO Auto-generated method stub
		
		logger.debug("onAcceptDataSm [{}] to [{}] ",dataSm.getSourceAddr(),dataSm.getDestAddress());
		return null;
	}

	@Override
	public void onAcceptAlertNotification(AlertNotification alertNotification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAcceptDeliverSm(DeliverSm deliverSm) throws ProcessRequestException {
		
		if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())) {
            // delivery receipt
            try {
                DeliveryReceipt delReceipt = deliverSm.getShortMessageAsDeliveryReceipt();
                long id = Long.parseLong(delReceipt.getId()) & 0xffffffff;
                String messageId = Long.toString(id, 16).toUpperCase();
                logger.info("Receiving delivery receipt for message '{}' : {}", messageId, delReceipt);
            } catch (InvalidDeliveryReceiptException e) {
            	logger.error("Failed getting delivery receipt", e);
            }
        } else {
            // regular short message
        	logger.info("Receiving message : {}", new String(deliverSm.getShortMessage()));
        }
		
	}

}
