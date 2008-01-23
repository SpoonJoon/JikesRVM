/*
 *  This file is part of the Jikes RVM project (http://jikesrvm.org).
 *
 *  This file is licensed to You under the Common Public License (CPL);
 *  You may not use this file except in compliance with the License. You
 *  may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/cpl1.0.php
 *
 *  See the COPYRIGHT.txt file distributed with this work for information
 *  regarding copyright ownership.
 */
package org.jikesrvm.compilers.opt.regalloc;

import org.jikesrvm.compilers.opt.CompilerPhase;
import org.jikesrvm.compilers.opt.OptOptions;
import org.jikesrvm.compilers.opt.OptimizationPlanAtomicElement;
import org.jikesrvm.compilers.opt.OptimizationPlanCompositeElement;
import org.jikesrvm.compilers.opt.OptimizationPlanElement;
import org.jikesrvm.compilers.opt.ir.IR;

/**
 * Driver routine for register allocation
 */
public final class RegisterAllocator extends OptimizationPlanCompositeElement {

  public RegisterAllocator() {
    super("Register Allocation", new OptimizationPlanElement[]{
        // 1. Prepare for the allocation
        new OptimizationPlanAtomicElement(new RegisterAllocPreparation()),
        // 2. Perform the allocation, using the live information
        new LinearScan()});
  }

  public boolean shouldPerform(OptOptions options) { return true; }

  public String getName() { return "RegAlloc"; }

  public boolean printingEnabled(OptOptions options, boolean before) {
    return options.PRINT_REGALLOC;
  }

  private static class RegisterAllocPreparation extends CompilerPhase {
    public final boolean shouldPerform(OptOptions options) {
      return true;
    }

    /**
     * Return this instance of this phase. This phase contains no
     * per-compilation instance fields.
     * @param ir not used
     * @return this
     */
    public CompilerPhase newExecution(IR ir) {
      return this;
    }

    public final String getName() {
      return "Register Allocation Preparation";
    }

    /**
     * create the stack manager
     */
    public final void perform(org.jikesrvm.compilers.opt.ir.IR ir) {
      ir.stackManager.prepare(ir);
    }
  }
}