## RC4 – Pseudocódigo

### Função principal: RC4(chave, dados)
1. Inicialize os vetores:
    - Para i de 0 até 255:
        - S[i] ← i
        - K[i] ← chave[i mod comprimento(chave)]

2. Executar KSA (Key Scheduling Algorithm):
    - j ← 0
    - Para i de 0 até 255:
        - j ← (j + S[i] + K[i]) mod 256
        - Troque S[i] com S[j]

3. Executar PRGA (Pseudo-Random Generation Algorithm):
    - i ← 0
    - j ← 0
    - Para cada byte em dados:
        - i ← (i + 1) mod 256
        - j ← (j + S[i]) mod 256
        - Troque S[i] com S[j]
        - t ← (S[i] + S[j]) mod 256
        - K_byte ← S[t]
        - byte_saida ← byte XOR K_byte
        - Adicione byte_saida ao resultado

4. Retorne o resultado
