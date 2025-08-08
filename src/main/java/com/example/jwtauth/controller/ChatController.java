package com.example.jwtauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.jwtauth.service.GoldRateService;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "false")
public class ChatController {

    @Autowired
    private GoldRateService goldRateService;

    private final NumberFormat inrFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String question = request.get("question").toLowerCase();
        String answer = generateAnswer(question);
        
        Map<String, String> response = new HashMap<>();
        response.put("answer", answer);
        return ResponseEntity.ok(response);
    }

    private String generateAnswer(String question) {
        try {
            if (question.contains("gold rate") || question.contains("gold price") || 
                question.contains("gold value") || question.contains("gold cost")) {
                return getGoldRateAnswer();
            }
            if (question.contains("silver rate") || question.contains("silver price") || 
                question.contains("silver value") || question.contains("silver cost")) {
                return getSilverRateAnswer();
            }
            if (question.contains("loan amount") || question.contains("how much loan") || 
                question.contains("loan value") || question.contains("borrow")) {
                return getLoanAmountAnswer();
            }
            if (question.contains("interest rate") || question.contains("interest") || 
                question.contains("emi") || question.contains("monthly payment")) {
                return getInterestRateAnswer();
            }
            if (question.contains("eligibility") || question.contains("qualify") || 
                question.contains("requirements") || question.contains("documents")) {
                return getEligibilityAnswer();
            }
            if (question.contains("process") || question.contains("how to") || 
                question.contains("steps") || question.contains("procedure")) {
                return getProcessAnswer();
            }
            if (question.contains("tenure") || question.contains("duration") || 
                question.contains("period") || question.contains("time")) {
                return getTenureAnswer();
            }
            return getDefaultAnswer();
        } catch (Exception e) {
            return "I apologize, but I'm having trouble processing your request right now. Please try again later or contact our support team.";
        }
    }

    private String getGoldRateAnswer() {
        try {
            Map<String, Object> goldRateData = goldRateService.getGoldRate();
            double goldRate = (Double) goldRateData.get("rate"); // INR per gram
            String source = (String) goldRateData.get("source");
            String sourceInfo = source.equals("live") ? "📡 Live market data" : "📊 Estimated rate";
            return String.format(
                "💰 Current Gold Rate: %s per gram\n\n" +
                "%s\n\n" +
                "📊 For gold loans, we offer 75%% of the gold's market value as loan amount.\n\n" +
                "💡 Examples:\n" +
                "• 10 grams of 24K gold ≈ %s loan\n" +
                "• 50 grams of 22K gold ≈ %s loan\n" +
                "• 100 grams of 18K gold ≈ %s loan\n\n" +
                "🔍 Contact us for precise valuation of your gold!",
                inrFormat.format(goldRate),
                sourceInfo,
                inrFormat.format(goldRateService.calculateGoldLoanAmount(10, 24)),
                inrFormat.format(goldRateService.calculateGoldLoanAmount(50, 22)),
                inrFormat.format(goldRateService.calculateGoldLoanAmount(100, 18))
            );
        } catch (Exception e) {
            return "💰 Current Gold Rate: ₹6,200 per gram\n\n" +
                   "📊 For gold loans, we offer 75% of the gold's market value as loan amount.\n\n" +
                   "💡 The exact rate may vary based on purity and current market conditions. Please contact us for the most accurate valuation.";
        }
    }

    private String getSilverRateAnswer() {
        try {
            Map<String, Object> silverRateData = goldRateService.getSilverRate();
            double silverRate = (Double) silverRateData.get("rate"); // INR per gram
            String source = (String) silverRateData.get("source");
            String sourceInfo = source.equals("live") ? "📡 Live market data" : "📊 Estimated rate";
            return String.format(
                "🥈 Current Silver Rate: %s per gram\n\n" +
                "%s\n\n" +
                "📊 For silver loans, we offer 65%% of the silver's market value as loan amount.\n\n" +
                "💡 Examples:\n" +
                "• 100 grams of silver ≈ %s loan\n" +
                "• 500 grams of silver ≈ %s loan\n" +
                "• 1 kg of silver ≈ %s loan\n\n" +
                "🔍 Contact us for precise valuation of your silver!",
                inrFormat.format(silverRate),
                sourceInfo,
                inrFormat.format(goldRateService.calculateSilverLoanAmount(100)),
                inrFormat.format(goldRateService.calculateSilverLoanAmount(500)),
                inrFormat.format(goldRateService.calculateSilverLoanAmount(1000))
            );
        } catch (Exception e) {
            return "🥈 Current Silver Rate: ₹75 per gram\n\n" +
                   "📊 For silver loans, we offer 65% of the silver's market value as loan amount.\n\n" +
                   "💡 The exact rate may vary based on purity and current market conditions.";
        }
    }

    private String getLoanAmountAnswer() {
        return "💼 Gold/Silver Loan Amount Calculation:\n\n" +
               "📏 Gold Loans: 75% of market value\n" +
               "📏 Silver Loans: 65% of market value\n\n" +
               "📋 Factors affecting loan amount:\n" +
               "• Purity of gold/silver (24K, 22K, 18K)\n" +
               "• Current market rates\n" +
               "• Weight of the metal\n" +
               "• Your credit profile\n\n" +
               "💡 Example: 100 grams of 22K gold ≈ " + inrFormat.format(goldRateService.calculateGoldLoanAmount(100, 22)) + " loan amount\n" +
               "💡 Example: 1 kg of silver ≈ " + inrFormat.format(goldRateService.calculateSilverLoanAmount(1000)) + " loan amount\n\n" +
               "🔍 Contact us for a precise valuation of your gold/silver!";
    }

    private String getInterestRateAnswer() {
        return "📈 Current Interest Rates:\n\n" +
               "💰 Gold Loans: 9% - 12% per annum\n" +
               "🥈 Silver Loans: 10% - 13% per annum\n\n" +
               "📊 EMI Calculation Example:\n" +
               "• Loan Amount: ₹1,00,000\n" +
               "• Interest Rate: 10% p.a.\n" +
               "• Tenure: 12 months\n" +
               "• Monthly EMI: ~₹8,792\n\n" +
               "💡 Interest rates may vary based on:\n" +
               "• Loan amount\n" +
               "• Tenure\n" +
               "• Credit score\n" +
               "• Market conditions\n\n" +
               "🎯 We offer competitive rates starting from 9%!";
    }

    private String getEligibilityAnswer() {
        return "✅ Gold/Silver Loan Eligibility:\n\n" +
               "📋 Basic Requirements:\n" +
               "• Age: 18-65 years\n" +
               "• Valid ID proof (Aadhaar, PAN, Driving License)\n" +
               "• Address proof\n" +
               "• Gold/Silver ornaments or coins\n" +
               "• Minimum weight: 10 grams for gold, 100 grams for silver\n\n" +
               "📄 Required Documents:\n" +
               "• Identity proof\n" +
               "• Address proof\n" +
               "• Income proof (optional)\n" +
               "• Gold/Silver items for valuation\n\n" +
               "💡 No credit score required!\n" +
               "💡 No income proof mandatory!\n" +
               "💡 Quick approval within 2 hours!";
    }

    private String getProcessAnswer() {
        return "🔄 Gold/Silver Loan Process:\n\n" +
               "1️⃣ Application: Fill online form or visit branch\n" +
               "2️⃣ Documentation: Submit required documents\n" +
               "3️⃣ Valuation: Gold/Silver tested for purity & weight\n" +
               "4️⃣ Approval: Loan amount determined (within 30 mins)\n" +
               "5️⃣ Disbursement: Money transferred to your account\n" +
               "6️⃣ Storage: Gold/Silver safely stored in our vault\n\n" +
               "⏱️ Total Time: 2-4 hours\n" +
               "💰 Processing Fee: 1-2% of loan amount\n" +
               "🔒 Security: Bank-grade vault storage\n\n" +
               "💡 You can redeem your gold/silver anytime by repaying the loan!";
    }

    private String getTenureAnswer() {
        return "⏰ Gold/Silver Loan Tenure Options:\n\n" +
               "📅 Available Tenures:\n" +
               "• 3 months\n" +
               "• 6 months\n" +
               "• 12 months\n" +
               "• 18 months\n" +
               "• 24 months\n" +
               "• 36 months (maximum)\n\n" +
               "💡 Flexible Repayment Options:\n" +
               "• Monthly EMI\n" +
               "• Quarterly payments\n" +
               "• Bullet payment at end\n" +
               "• Partial prepayment allowed\n\n" +
               "🎯 Recommended: 12-24 months for optimal EMI management\n" +
               "⚡ Early closure: No prepayment charges after 6 months!";
    }

    private String getDefaultAnswer() {
        return "🤖 Welcome to Star Finance Gold/Silver Loan Assistant!\n\n" +
               "I can help you with:\n\n" +
               "💰 Gold & Silver Rates (in ₹)\n" +
               "• Current market prices\n" +
               "• Rate trends\n" +
               "• Valuation estimates\n\n" +
               "💼 Loan Information\n" +
               "• Loan amounts\n" +
               "• Interest rates\n" +
               "• EMI calculations\n" +
               "• Tenure options\n\n" +
               "📋 Process & Requirements\n" +
               "• Eligibility criteria\n" +
               "• Required documents\n" +
               "• Application process\n" +
               "• Timeline\n\n" +
               "💡 Just ask me anything about gold/silver loans!";
    }
} 