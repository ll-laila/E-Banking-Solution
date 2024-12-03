import { AgentComponentsModule } from './AgentComponents.module';

describe('ComponentsModule', () => {
  let componentsModule: AgentComponentsModule;

  beforeEach(() => {
    componentsModule = new AgentComponentsModule();
  });

  it('should create an instance', () => {
    expect(componentsModule).toBeTruthy();
  });
});
