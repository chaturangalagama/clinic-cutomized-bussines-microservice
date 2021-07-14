package com.ilt.cms.pm.integration.impl.clinic.billing;

import com.ilt.cms.api.entity.charge.InvoiceView;
import com.ilt.cms.core.entity.billing.ItemChargeDetail;
import com.ilt.cms.core.entity.sales.Invoice;
import com.ilt.cms.core.entity.sales.PaymentInfo;
import com.ilt.cms.downstream.clinic.billing.ChargingApiService;
import com.ilt.cms.pm.business.service.clinic.billing.InvoiceService;
import com.ilt.cms.pm.integration.mapper.clinic.billing.InvoiceMapper;
import com.lippo.cms.exception.CMSException;
import com.lippo.commons.util.CommonUtils;
import com.lippo.commons.util.StatusCode;
import com.lippo.commons.web.api.ApiResponse;
import com.lippo.commons.web.api.HttpApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Transient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.lippo.commons.web.CommonWebUtil.httpApiResponse;

/**
 * <p>
 * <code>{@link DefaultChargingApiService}</code> -
 * Default implementation for the charging request adaptation interface.
 * </p>
 *
 * @author prabath.
 */
@Service
public class DefaultChargingApiService implements ChargingApiService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultChargingApiService.class);

    private InvoiceService invoiceService;
    private InvoiceMapper invoiceMapper;

    public DefaultChargingApiService(InvoiceService invoiceService,
                                     InvoiceMapper invoiceMapper) {
        this.invoiceService = invoiceService;
        this.invoiceMapper = invoiceMapper;
    }

    public ResponseEntity<ApiResponse> deleteInvoice(String caseId, String invoiceIdList, String reason) {
        logger.info("Request received to the invoices [" + invoiceIdList + "] for the Case Id [" + caseId + "] " +
                "with a reason [" + reason + "]");
        try {
            List<Invoice> invoiceList = invoiceService.deleteInvoice(caseId, Arrays.asList(invoiceIdList.split(",")), reason);
            List<InvoiceView> invoiceViews = invoiceList.stream().map(invoiceMapper::mapToApiEntity).collect(Collectors.toList());
            return httpApiResponse(new HttpApiResponse(invoiceViews));
        } catch (CMSException e) {
            logger.error("Error occurred while deleting the invoice [" + invoiceIdList + "]", e);
            return httpApiResponse(new HttpApiResponse(e.getStatusCode(), e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getPaymentModes() {
        logger.info("Request received to retrieve payment modes");
        return httpApiResponse(new HttpApiResponse(invoiceService.getPaymentModes()));
    }

    @Override
    public ResponseEntity<ApiResponse> makeDirectPaymentForCase(String caseId, List<PaymentInfo> paymentInfos) {

        logger.info("Request received to make a direct payment to the Case Id [" + caseId + "]. " +
                "Paying [" + paymentInfos + "]");
        try {
            List<Invoice> invoiceList = invoiceService.makeDirectPaymentForCase(caseId, paymentInfos);
            return httpApiResponse(new HttpApiResponse(invoiceList));
        } catch (CMSException e) {
            logger.error("Error occurred while making a direct payment", e);
            return httpApiResponse(new HttpApiResponse(e.getStatusCode(), e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<ApiResponse> calculateDueAmountForCase(String caseId) {
        logger.info("Request received to calculate the due amount for the Case [" + caseId + "]");
        try {
            int dueAmountForCase = invoiceService.calculateDueAmountForCase(caseId);
            return httpApiResponse(new HttpApiResponse(dueAmountForCase));
        } catch (CMSException e) {
            logger.error("Error occurred while calculating the due amount ", e);
            return httpApiResponse(new HttpApiResponse(e.getStatusCode(), e.getMessage()));
        }
    }


    @Override
    public ResponseEntity<ApiResponse> findInvoiceBreakdownForCase(String caseId) {
//        try {

//            List<Invoice> invoiceBreakdownForItems = newCaseService.findCaseInvoices(caseId); ---commented---

            List<Invoice> invoiceList = new ArrayList<>();
            Invoice invoice = new Invoice();
            invoice.setInvoiceState(Invoice.InvoiceState.INITIAL);
            invoice.setInvoiceType(Invoice.InvoiceType.DIRECT);
            invoice.setVisitId("60eddcfd656b4f32e4600c22");
            invoice.setInvoiceNumber("20211941000001");

            List<PaymentInfo> paymentInfoList = new ArrayList<>();
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setAmount(100);
            paymentInfo.setBillMode(Invoice.PaymentMode.CASH);
            paymentInfo.setBillTransactionId("20211941000001");
            paymentInfo.setExternalTransactionId("20211941000001");
            paymentInfo.setCashAdjustment(10);
            paymentInfo.setInvoiceNumber("20211941000001");
//                private String billTransactionId;
//                private Invoice.PaymentMode billMode;
//                private int amount;
//                private String externalTransactionId;
//                private int cashAdjustment;
//                private String invoiceNumber;
            paymentInfoList.add(paymentInfo);
            invoice.setPaymentInfos(paymentInfoList);
//            invoice.setInvoiceTime();
            invoice.setPayableAmount(100);
            invoice.setPaidAmount(20);
            invoice.setTaxAmount(10);
//            "visitNumber":"20211941000001","patientId":"5bf62ab3dbea1b39c2675ea7","clinicId":"5b55aabd0550de0021097b5b","preferredDoctorId":"D0001","visitPurpose":"Consultation","priority":"NORMAL","patientReferralEntity":{"planMaxUsage":null,"consultation":{"consultationId":"60edcd62656b4f1b547bd18b","patientId":"5bf62ab3dbea1b39c2675ea7","consultationNotes":"<p>Building upon my first tip, you should <span style=\"font-size:18px\"><u><strong>always recommend a clear course</strong></u></span> of action to the referring physician, even if you&#39;re not exactly sure how the patient should be treated. In that case, you can certainly recommend that the referring physician perform more tests or even seek out other consults.</p>\n","memo":"","clinicNotes":"‘Dear Mr Harris, Just a few lines to commend you and your team on a superb job. At the initial consultation, Maddie felt very comfortable with you, and very much liked the way you talked directly to her and asked her how she was feeling. She felt in control and was actually looking forward to the surgery, partly because there was a promise of feeling well after so months of illness, but also because she felt confident that you would ‘make everything better’.","doctorId":"D0001","clinicId":"5b55aabd0550de0021097b5b","consultationStartTime":"13-07-2021T22:59:06","consultationEndTime":null},"diagnosisIds":null,"medicalCertificates":[],"consultationFollowup":null,"patientReferral":null,"dispatchItemEntities":[{"purchasedId":null,"itemId":"5c18c9a94723409073e8ea49","dosageUom":"TABLET","dosageInstruction":"INJECTIU","instruct":"N/ TO COMP","duration":1,"dosage":7,"quantity":1,"oriTotalPrice":500,"batchNo":"","expiryDate":"22-07-2021","remarks":"","itemPriceAdjustment":{"adjustedValue":2000,"paymentType":"DOLLAR"},"itemCode":null,"itemName":null}]},"visitStatus":"POST_CONSULT","startTime":"13-07-2021T22:31:29","endTime":null,"remark":"","clinicName":null,"diagnosisEntities":null,"patientQueue":{"queueNumber":101,"urgent":false,"patientCalled":true}},"patientName":"patient1","userId":{"idType":"PASSPORT","number":"P000000004"}},{"registryEntity":{"visitId":"60edc73b656b4f0c4c00f7fc","visitNumber":"20211941000002","patientId":"5bf62ab3dbea1b39c2675ea7","clinicId":"5b55aabd0550de0021097b5b","preferredDoctorId":"D0001","visitPurpose":"Consultation","priority":"NORMAL","patientReferralEntity":{"planMaxUsage":null,"consultation":null,"diagnosisIds":null,"medicalCertificates":[],"consultationFollowup":null,"patientReferral":null,"dispatchItemEntities":[]},"visitStatus":"INITIAL","startTime":"13-07-2021T22:32:51","endTime":null,"remark":"","clinicName":null,"diagnosisEntities":null,"patientQueue":{"queueNumber":102,"urgent":false,"patientCalled":false}},"patientName":"patient1","userId":{"idType":"PASSPORT","number":"P000000004"}}]}
//                    invoice.set
//                    invoice.set
//                    invoice.set
//                    invoice.set
//                    invoice.set
//                    invoice.set
//            public enum PaymentMode {
//                CASH, NETS, VISA, MASTER_CARD, AMERICAN_EXPRESS, JCB, OTHER_CREDIT_CARD, CHEQUE, GIRO, CREDIT;
//            }
//            private String visitId;
//            private String invoiceNumber;
//            private Invoice.InvoiceType invoiceType;
//            private List<PaymentInfo> paymentInfos = new ArrayList<>();
//            private LocalDateTime invoiceTime;
//            private LocalDateTime paidTime;
//            private int payableAmount;
//            private int paidAmount;
//            private int taxAmount;
//            private int cashAdjustmentRounding;
//            private String planId;
//            @Transient
//            private String planName;
//            private Invoice.InvoiceState invoiceState;
//            private String reasonForDelete;
            invoiceList.add(invoice);
            return httpApiResponse(new HttpApiResponse(invoiceList));
//        } catch (CMSException e) {
//            logger.error("Error processing invoices", e);
//            return httpApiResponse(new HttpApiResponse(e.getStatusCode(), e.getMessage()));
//        }
    }

//    @Override
//    public ResponseEntity<ApiResponse> findInvoiceBreakdownForCase(String caseId, ItemChargeDetail.ItemChargeRequest itemChargeRequest) {
//        try {
//            for (ItemChargeDetail itemChargeDetail : itemChargeRequest.getChargeDetails()) {
//                if (!CommonUtils.isStringValid(itemChargeDetail.getItemId())) {
//                    logger.error("Invalid item id for [" + itemChargeDetail + "]");
//                    return httpApiResponse(new HttpApiResponse(StatusCode.E2000, "Invalid item id"));
//                }
//            }
//            List<Invoice> invoiceBreakdownForItems = newCaseService.invoiceBreakdown(caseId, itemChargeRequest);
//            return httpApiResponse(new HttpApiResponse(invoiceBreakdownForItems));
//        } catch (CMSException e) {
//            logger.error("Error processing invoices", e);
//            return httpApiResponse(new HttpApiResponse(e.getStatusCode(), e.getMessage()));
//        }
//    }

}
