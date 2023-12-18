package appendix

import chap23.LockSemaphoreTests

class SynchronizationTests
    extends LockSemaphoreTests(SynchronizationJ.SimpleLock(), _.lock(), _.unlock())
