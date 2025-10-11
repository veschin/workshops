import React, { useState, useEffect } from 'react';

export const UseEffectHell: React.FC = () => {
  const [count, setCount] = useState(0);
  const [multiplier, setMultiplier] = useState(2);

  // Problem: stale closure - multiplier captured at effect creation time
  useEffect(() => {
    const timer = setInterval(() => {
      console.log('Count with multiplier:', count * multiplier); // Uses stale multiplier
      setCount(prev => prev + 1);
    }, 1000);

    return () => clearInterval(timer);
  }, []); // Empty dependency array - effect runs only once, captures initial multiplier

  // Problem: missing dependency - multiplier not in deps
  useEffect(() => {
    document.title = `Count: ${count * multiplier}`;
  }, [count]); // Missing multiplier - title won't update when multiplier changes

  return (
    <div>
      <h3>React useEffect Hell</h3>
      <p>Count: {count}</p>
      <p>Multiplier: {multiplier}</p>
      <p>Product: {count * multiplier}</p>
      <button onClick={() => setMultiplier(m => m + 1)}>Increase Multiplier</button>
      <p>Check console for stale closure issue</p>
    </div>
  );
};