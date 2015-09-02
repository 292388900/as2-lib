/**
 * The FreeBSD Copyright
 * Copyright 1994-2008 The FreeBSD Project. All rights reserved.
 * Copyright (C) 2013-2015 Philip Helger philip[at]helger[dot]com
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *    1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE FREEBSD PROJECT ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE FREEBSD PROJECT OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation
 * are those of the authors and should not be interpreted as representing
 * official policies, either expressed or implied, of the FreeBSD Project.
 */
package com.helger.as2lib.processor.resender;

import java.util.Date;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.as2lib.message.IMessage;
import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;

/**
 * This class represents a single in-memory item to be resend.
 *
 * @author Philip Helger
 */
@Immutable
public class ResendItem
{
  final String m_sAction;
  final int m_nRetries;
  final IMessage m_aMsg;
  private final long m_nEarliestResendDateMS;

  public ResendItem (@Nonnull @Nonempty final String sAction,
                     @Nonnegative final int nRetries,
                     @Nonnull final IMessage aMsg,
                     @Nonnegative final long nResendDelayMS)
  {
    m_sAction = ValueEnforcer.notEmpty (sAction, "Action");
    m_nRetries = ValueEnforcer.isGE0 (nRetries, "Retries");
    m_aMsg = ValueEnforcer.notNull (aMsg, "Message");
    ValueEnforcer.isGE0 (nResendDelayMS, "ResendDelayMS");
    m_nEarliestResendDateMS = new Date ().getTime () + nResendDelayMS;
  }

  @Nonnull
  @Nonempty
  public String getAction ()
  {
    return m_sAction;
  }

  @Nonnegative
  public int getRetries ()
  {
    return m_nRetries;
  }

  @Nonnull
  public IMessage getMessage ()
  {
    return m_aMsg;
  }

  @Nonnull
  public Date getEarliestResendDate ()
  {
    return new Date (m_nEarliestResendDateMS);
  }

  public boolean isTimeToSend ()
  {
    return m_nEarliestResendDateMS <= new Date ().getTime ();
  }
}