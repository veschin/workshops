import React, { useState } from 'react';
import { Button, TextField } from '@mui/material';

const FormApp: React.FC = () => {
  const [name, setName] = useState('');

  const submit = () => {
    console.log('Submitted:', name);
  };

  return (
    <div>
      <h3>React MUI (Native)</h3>
      <TextField 
        label="Name" 
        value={name} 
        onChange={e => setName(e.target.value)} 
      />
      <Button variant="contained" onClick={submit}>
        Submit
      </Button>
      <p>MUI is native in React - no interop issues.</p>
    </div>
  );
};

export default FormApp;