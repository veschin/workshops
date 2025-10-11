import React, { useState, useMemo, useCallback, memo } from 'react';

// Problem: manual memoization everywhere
const ExpensiveComponent = memo(({ data, onClick }: { data: number[]; onClick: () => void }) => {
  console.log('ExpensiveComponent rendered');
  const sum = useMemo(() => {
    console.log('Calculating sum...');
    return data.reduce((a, b) => a + b, 0);
  }, [data]);

  return (
    <div>
      <p>Sum: {sum}</p>
      <button onClick={onClick}>Click me</button>
    </div>
  );
});

export const PerformanceMemoProblem: React.FC = () => {
  const [count, setCount] = useState(0);
  const [data, setData] = useState([1, 2, 3, 4, 5]);

  // Problem: need useCallback to prevent re-renders
  const increment = useCallback(() => {
    setCount(c => c + 1);
  }, []);

  // Problem: useMemo for expensive calculations
  const doubledData = useMemo(() => {
    console.log('Doubling data...');
    return data.map(x => x * 2);
  }, [data]);

  const addNumber = useCallback(() => {
    setData(prev => [...prev, Math.floor(Math.random() * 10)]);
  }, []);

  return (
    <div>
      <h3>React Performance Memo Problem</h3>
      <p>Count: {count}</p>
      <p>Doubled data: {doubledData.join(', ')}</p>
      <button onClick={increment}>Increment</button>
      <button onClick={addNumber}>Add Random Number</button>
      <ExpensiveComponent data={data} onClick={increment} />
      <p>Check console - components re-render unnecessarily without proper memoization</p>
    </div>
  );
};