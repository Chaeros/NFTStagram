package com.example.stagram;

import com.klaytn.caver.Caver;
import com.klaytn.caver.contract.SendOptions;
import com.klaytn.caver.kct.kip17.KIP17;
import com.klaytn.caver.methods.response.Bytes32;
import com.klaytn.caver.transaction.AbstractTransaction;
import com.klaytn.caver.transaction.TxPropertyBuilder;
import com.klaytn.caver.transaction.type.ValueTransferMemo;
import com.klaytn.caver.utils.Utils;
import com.klaytn.caver.wallet.keyring.SingleKeyring;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Blockchain {
    String address = "0x81fe9f0b45dee0c6ca17c2d6d492c0491a306261";
    String destination = "0xd045f3E738fFE5E6df6acabB561D0505f666ca76";
    String private_key = "0xc4d9ef7e10b3c48677ea1e5569a6ba2edc29264206e12cc6117bdc6c180aae1f";
    String BAO = "https://api.baobab.klaytn.net:8651/";
    String BAO_API = "https://public-node-api.klaytnapi.com/v1/baobab";
    String contract_address = "0xd4f27d65ba5186763584ea9ae6457bb587cfb485";
    String temp_contract_address = "0x934eee0a648bf3fbab3401c1d94a96bd36d8e4b2";
    Caver caver = new Caver(BAO_API);

    public void contract(String creator_private_key, String name, String symbol){
        SingleKeyring keyring = (SingleKeyring) caver.wallet.keyring.createFromPrivateKey(creator_private_key);
        caver.wallet.add(keyring);
        new Thread(() -> {
            try {
                caver.kct.kip17.deploy(keyring.getAddress(),name, symbol);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public int mint_NFT(String img ,String minter_private_key, String address_to) throws InterruptedException {
        AtomicInteger ret = new AtomicInteger();
        new Thread(() -> {
            try {
                SingleKeyring keyring = (SingleKeyring) caver.wallet.keyring.createFromPrivateKey(minter_private_key);
                caver.wallet.add(keyring);
                KIP17 kip17 = new KIP17(caver, contract_address);

                BigInteger token_id = kip17.totalSupply();
                ret.set(token_id.intValue());
                //현재 총 발행수가 토큰 id가 됨.

                SendOptions newSendOptions = new SendOptions();
                newSendOptions.setFrom(keyring.getAddress());
                newSendOptions.setGas(BigInteger.valueOf(55550000));
                kip17.mintWithTokenURI(address_to, token_id, img, newSendOptions);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(500);
        return ret.get();
    }

    public String get_NFT_info(Integer token_id) throws InterruptedException {
        AtomicReference<String> ret = new AtomicReference<>("");
        new Thread(() -> {
            try {
                KIP17 kip17 = new KIP17(caver, contract_address);
                ret.set(kip17.tokenURI(BigInteger.valueOf(token_id)));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1500);
        return String.valueOf(ret);
    }

    public void send() throws IOException, CipherException, TransactionException {

        String memo = "클레이튼에 등록되는 메모";
        String input = Numeric.toHexString(memo.getBytes(StandardCharsets.UTF_8));
        int peb_klay = 1; //클레이튼 peb단위.

        Caver caver = new Caver(BAO_API);

        SingleKeyring keyring = (SingleKeyring) caver.wallet.keyring.createFromPrivateKey(private_key);
        caver.wallet.add(keyring);

        //Create a value transfer transaction
        ValueTransferMemo valueTransferMemo = caver.transaction.valueTransferMemo.create(
                TxPropertyBuilder.valueTransferMemo()
                        .setFrom(keyring.getAddress())
                        .setTo(destination)
                        .setValue(new BigInteger(
                                caver.utils.convertToPeb(
                                        BigDecimal.valueOf(peb_klay),
                                        Utils.KlayUnit.kpeb
                                )
                        ))
                        .setGas(BigInteger.valueOf(5555000))
                        .setInput(input)
        );

        // 스레드를 사용하여 네트워크 요청
        new Thread(() -> {
            try {
                AbstractTransaction at = caver.wallet.sign(keyring.getAddress(), valueTransferMemo);
                //Send a transaction to the klaytn blockchain platform (Klaytn)
                Bytes32 result = caver.rpc.klay.sendRawTransaction(valueTransferMemo.getRawTransaction()).send();
                if(result.hasError()) {
                    throw new RuntimeException(result.getError().getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
