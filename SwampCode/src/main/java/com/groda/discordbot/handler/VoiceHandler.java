package com.groda.discordbot.handler;

import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;
import net.dv8tion.jda.api.audio.UserAudio;

import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class VoiceHandler implements AudioSendHandler, AudioReceiveHandler {

    private final Queue<byte[]> queue = new ConcurrentLinkedQueue<>();
    private final Queue<byte[]> queueGabri = new ConcurrentLinkedQueue<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canReceiveCombined() {
        // limit queue to 10 entries, if that is exceeded we can not receive more until the send system catches up
        return queueGabri.size() < 1000;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCombinedAudio(CombinedAudio combinedAudio) {
        // we only want to send data when a user actually sent something, otherwise we would just send silence
        if (combinedAudio.getUsers().isEmpty())
            return;

        byte[] data = combinedAudio.getAudioData(.2f); // volume at 100% = 1.0 (50% = 0.5 / 55% = 0.55)
        queue.add(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override // give audio separately for each user that is speaking
    public boolean canReceiveUser() {
        // this is not useful if we want to echo the audio of the voice channel, thus disabled for this purpose
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleUserAudio(UserAudio userAudio) {
        if (userAudio == null)
            return;

        String userName = userAudio.getUser().getName();

        if (!userName.equalsIgnoreCase("chriccro"))
            return;


        byte[] data = userAudio.getAudioData(1.0f);

        queueGabri.add(data);
    }


    /* Send Handling */

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canProvide() {
        // If we have something in our buffer we can provide it to the send system
        return !queue.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteBuffer provide20MsAudio() {
        // use what we have in our buffer to send audio as PCM
        byte[] data = queueGabri.poll();
        return data == null ? null : ByteBuffer.wrap(data); // Wrap this in a java.nio.ByteBuffer
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOpus() {
        // since we send audio that is received from discord we don't have opus but PCM
        return false;
    }
}
