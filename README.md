# Minecraft Clone (Java + LWJGL)

Este projeto é um **clone simplificado de Minecraft**, feito em **Java** utilizando a biblioteca **LWJGL (Lightweight Java Game Library)** para gráficos, entrada de teclado/mouse e gerenciamento de janela.

Atualmente ele consegue:
- Criar uma janela 3D usando **GLFW**.
- Renderizar cubos com **texturas carregadas** via **STBImage**.
- Controlar a câmera com **WASD + Espaço/Shift + Mouse**.
- Suportar profundidade e perspectiva 3D.

---

## 📚 Tecnologias usadas

### **LWJGL (Lightweight Java Game Library)**
É a ponte entre Java e bibliotecas nativas de baixo nível:
- **GLFW** → criação de janelas e input (teclado/mouse).
- **OpenGL** → renderização 3D.
- **STB** → carregamento de imagens (texturas).

---

### 🔹 GLFW (janela e input)
Funções que começam com `glfw...`:
- `glfwInit()` → inicializa.
- `glfwCreateWindow()` → cria a janela.
- `glfwPollEvents()` → processa entrada.
- `glfwGetKey(window, GLFW_KEY_W)` → verifica se uma tecla está pressionada.
- `glfwSetCursorPosCallback(...)` → callback do mouse.

### 🔹 OpenGL (renderização)
Funções que começam com `gl...`:
- `glEnable(GL_DEPTH_TEST)` → ativa profundidade.
- `glClearColor(...)` e `glClear(...)` → limpa tela.
- `glTranslatef`, `glRotatef` → transformações.
- `glBindTexture`, `glTexImage2D` → texturas.
- `glBegin(GL_QUADS) ... glEnd()` → desenho de cubos (modo legado).

### 🔹 STB (imagens)
- `stbi_load(path, ...)` → carrega imagem.
- `stbi_image_free(image)` → libera memória.
- `stbi_failure_reason()` → erro ao carregar.

---

## 🧩 O que já é do jogo (seu código)

- `Main.java`
    - `run()` → ciclo principal.
    - `init()` → inicialização da janela, OpenGL e texturas.
    - `loop()` → game loop (`input → update → render`).
    - `processInput()` → processa teclado/mouse.
    - `perspectiveGL()` → define a perspectiva 3D.
    - `loadTexture()` → carrega texturas via STB.
    - `drawCube()` → desenha um cubo texturizado.

---

## 📂 Estrutura sugerida

Para organizar melhor o código, separar em pacotes:
src/main/java/com/minecraftclone/
├── Main.java # ponto de entrada
├── engine/ # engine básica
│ ├── Window.java # cria janela
│ ├── Input.java # controla teclado/mouse
│ ├── Texture.java # carrega texturas
│ └── Shader.java # (futuro) shaders
├── graphics/
│ ├── Camera.java # posição, rotação, view matrix
│ └── Renderer.java # desenha cubos/chunks
├── world/
│ ├── Block.java
│ ├── Chunk.java
│ └── World.java


---

## ⚡ Controles atuais

- `WASD` → mover câmera (frente, trás, lados).
- `Espaço` → subir (voar).
- `Shift Esquerdo` → descer.
- `Mouse` → rotacionar câmera.
- `ESC` → fecha o programa.

---

## 🚀 Próximos passos

- Migrar de **OpenGL legado** (`glBegin`, `glEnd`) para **OpenGL moderno** (VAO, VBO, shaders).
- Criar `Chunk` para agrupar blocos em vez de renderizar cubo por cubo.
- Implementar **deltaTime** para movimento suave independente do FPS.
- Adicionar um **AssetManager** para centralizar carregamento de recursos.
- Criar sistema de **iluminação e sombras**.
- Começar a gerar **mundo procedural**.

---

## 🎮 Como rodar

1. Clone o repositório.
2. Tenha **Java 17+** instalado.
3. Configure no `pom.xml` a dependência do **LWJGL**.
4. Execute:

```bash
mvn compile exec:java -Dexec.mainClass="com.minecraftclone.Main"

